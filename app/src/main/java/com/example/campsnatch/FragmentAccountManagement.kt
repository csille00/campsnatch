package com.example.campsnatch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.campsnatch.databinding.FragAccountManagementBinding
import com.example.campsnatch.support.viewBinding

class FragmentAccountManagement:Fragment() {

    private val binding: FragAccountManagementBinding? by viewBinding(FragAccountManagementBinding::bind)
    private var cont: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cont = activity?.applicationContext
        return FragAccountManagementBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}