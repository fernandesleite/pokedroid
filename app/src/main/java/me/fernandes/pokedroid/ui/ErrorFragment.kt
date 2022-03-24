package me.fernandes.pokedroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.fernandes.pokedroid.databinding.FragmentErrorBinding


private const val ARG_ERROR_TEXT = "errorText"

class ErrorFragment : Fragment() {
    private var errorText: String? = null

    private lateinit var binding: FragmentErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            errorText = it.getString(ARG_ERROR_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(inflater)
        binding.errorMessage.text = errorText
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(errorText: String) =
            ErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ERROR_TEXT, errorText)
                }
            }
    }
}