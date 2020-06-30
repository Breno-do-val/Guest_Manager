package br.com.breno.convidados.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.breno.convidados.service.model.GuestModel
import br.com.breno.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository : GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest : LiveData<Boolean> = mSaveGuest

    fun save(name: String, presence : Boolean) {
        val guest = GuestModel(name= name, presence = presence)
        mSaveGuest.value = mGuestRepository.save(guest)
    }
}