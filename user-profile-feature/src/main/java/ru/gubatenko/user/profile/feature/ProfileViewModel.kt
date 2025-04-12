package ru.gubatenko.user.profile.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gubatenko.domain.auth.LogoutUseCase
import ru.gubatenko.user.domain.DeleteAccountUseCase

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke(LogoutUseCase.Args.Empty)
        }
    }

    fun delete() {
        viewModelScope.launch {
            deleteAccountUseCase.invoke(DeleteAccountUseCase.Args.Empty)
        }
    }
}