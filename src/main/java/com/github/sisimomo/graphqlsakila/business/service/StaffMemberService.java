package com.github.sisimomo.graphqlsakila.business.service;

import com.github.sisimomo.graphqlsakila.business.service.error.StaffMemberServiceError;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StaffMemberMapper;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.dao.repository.StaffMemberRepository;
import com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath.StaffMemberDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberUpdate;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StaffMemberService
    extends BaseEntityGraphUuidCrudService<StaffMemberEntity, StaffMemberCreate, StaffMemberUpdate> {

  private final StaffMemberRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  StaffMemberService(StaffMemberRepository repository, StaffMemberMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<StaffMemberEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, StaffMemberDtoPathToDaoPath.class, pageRequest);
  }

  public List<StaffMemberEntity> getByPaymentUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM staff AS a RIGHT JOIN payment AS b ON b.staff_id = a.id", uuids, StaffMemberEntity.class);
  }

  public List<StaffMemberEntity> getAllByRentalUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM staff AS a RIGHT JOIN rental AS b ON b.staff_id = a.id", uuids, StaffMemberEntity.class);
  }

  public List<StaffMemberEntity> getAllByStoreUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM staff AS a RIGHT JOIN store AS b ON b.manager_staff_id = a.id", uuids,
        StaffMemberEntity.class);
  }

  @Override
  protected void validateCreateDto(@NotNull StaffMemberCreate createDto) throws UncheckedException {
    throwIfPasswordTooWeak(createDto.getPassword());
  }

  /**
   * Throws an {@link UncheckedException} the password doesn't respect the constraint.
   *
   * @param password The password to check.
   */
  public void throwIfPasswordTooWeak(String password) {
    org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
        // Rule 1: Password length should be in between 8 and 99 characters.
        new LengthRule(8, 99),
        // Rule 2: At least one Upper-case character.
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        // Rule 3: At least one Lower-case character.
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        // Rule 4: At least one digit.
        new CharacterRule(EnglishCharacterData.Digit, 1),
        // Rule 5: At least one special character.
        new CharacterRule(EnglishCharacterData.Special, 1),
        // Rule 6: Prevents illegal sequences of alphabetical characters.
        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
        // Rule 7: Prevents illegal sequences of numeric characters.
        new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
        // Rule 8: Prevents illegal Qwerty-keyboard characters sequences.
        new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, false),
        // Rule 9: No whitespace allowed
        new WhitespaceRule()));
    RuleResult result = validator.validate(new PasswordData(password));
    if (!result.isValid()) {
      throw new UncheckedException(StaffMemberServiceError.PASSWORD_TOO_WEAK, log::warn,
          String.join(" ", validator.getMessages(result)));
    }
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(StaffMemberServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
