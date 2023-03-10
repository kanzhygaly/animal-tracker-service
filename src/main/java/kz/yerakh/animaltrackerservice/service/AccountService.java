package kz.yerakh.animaltrackerservice.service;

import kz.yerakh.animaltrackerservice.dto.AccountRequest;
import kz.yerakh.animaltrackerservice.dto.AccountResponse;
import kz.yerakh.animaltrackerservice.dto.AccountSearchCriteria;

import java.util.List;

public interface AccountService {

    AccountResponse addNewAccount(AccountRequest accountRequest);

    AccountResponse getAccount(Integer accountId);

    List<AccountResponse> searchAccounts(AccountSearchCriteria accountSearchCriteria);

    AccountResponse updateAccount(Integer accountId, AccountRequest accountRequest);

    void deleteAccount(Integer accountId);
}
