package com.bangkit.fica.presentation.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bangkit.fica.BuildConfig
import com.bangkit.fica.databinding.FragmentScanBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


class ScanFragment : Fragment() {
    private var _binding                : FragmentScanBinding?      = null
    private val binding get()                                       = _binding!!
    private val scanViewModel           : ScanViewModel             by viewModel()
    private var getFile                 : File?                     = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            binding.ivPicturePredict.setImageBitmap(imageBitmap)
            setImage()
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data!!.data
            binding.ivPicturePredict.setImageURI(imageUri)
            setImage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()
        setImage()
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(){
        scanViewModel.loadingLiveData.observe(viewLifecycleOwner){
            showLoading(it)
        }

        scanViewModel.predictLiveData.observe(viewLifecycleOwner){
            with(binding){
                val prob1 = (it.prediction!!.jsonMember0!!.probability!! * 100).toInt()
                val prob2 = (it.prediction.jsonMember1!!.probability!! * 100).toInt()
                val prob3 = (it.prediction.jsonMember2!!.probability!! * 100).toInt()
                tvProbability.text = it.prediction.jsonMember0!!.name
                    .plus(" = ")
                    .plus(prob1.toString())
                    .plus("%")
                    .plus(", ")
                    .plus(it.prediction.jsonMember1.name)
                    .plus(" = ")
                    .plus(prob2.toString())
                    .plus("%")
                    .plus(", ")
                    .plus(it.prediction.jsonMember2.name)
                    .plus(" = ")
                    .plus(prob3.toString())
                    .plus("%")

                when {
                    prob1 >= prob2 && prob1 >= prob3 -> {
                        tvName.text = it.prediction.jsonMember0.name
                        tvMax.text  = "Rp. ".plus(it.prediction.jsonMember0.maxPrice)
                        tvMin.text  = "Rp. ".plus(it.prediction.jsonMember0.minPrice)
                        tvDescription.text = it.prediction.jsonMember0.description
                    }
                    prob2 >= prob1 && prob2 >= prob3 -> {
                        tvName.text = it.prediction.jsonMember1.name
                        tvMax.text  = "Rp. ".plus(it.prediction.jsonMember1.maxPrice)
                        tvMin.text  = "Rp. ".plus(it.prediction.jsonMember1.minPrice)
                        tvDescription.text = it.prediction.jsonMember1.description
                    }
                    else -> {
                        tvName.text = it.prediction.jsonMember2.name
                        tvMax.text  = "Rp. ".plus(it.prediction.jsonMember2.maxPrice)
                        tvMin.text  = "Rp. ".plus(it.prediction.jsonMember2.minPrice)
                        tvDescription.text = it.prediction.jsonMember2.description
                    }
                }
            }
        }
    }

    private fun initClickListener(){
        with(binding){
            btnCamera.setOnClickListener {
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1)
            }

            btnGallery.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 2)
            }

            btnPredict.setOnClickListener {
                val root = Environment.getExternalStorageDirectory().toString()
                getFile = File("$root/Android/media/${BuildConfig.APPLICATION_ID}/FiCa/koi.jpg")
                if (getFile != null) {
                    Timber.e(getFile!!.name)
                    val requestImageFile = getFile!!.asRequestBody("image/*".toMediaTypeOrNull())
                    val imageMultiPart: MultipartBody.Part = createFormData(
                        "file",
                        getFile!!.name,
                        requestImageFile
                    )
                    scanViewModel.getPredict(imageMultiPart)
                } else {
                    Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setImage(){
        val bitmap = imageToBitmap()
        try {
            val root = Environment.getExternalStorageDirectory().toString()
            val file = File("$root/Android/media/${BuildConfig.APPLICATION_ID}/FiCa/koi.jpg")
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.flush()
            out.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun imageToBitmap(): Bitmap{
        return binding.ivPicturePredict.drawable.toBitmap()
    }

    private fun showLoading(state: Boolean) {
        binding.progressCircular.isVisible = state
    }


}