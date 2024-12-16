package com.golden.gate.ui.main.home.dialogs

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.room.AppDataBase
import com.golden.gate.core.room.RoomArticles
import com.golden.gate.databinding.DialogAddCarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class AddCarDialog : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogAddCarBinding::bind)
    private var selectedUri: Uri? = null
    private val room = AppDataBase.getInstance()
    lateinit var onSaveListener: () -> Unit

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launchImagePicker()
            } else {
                Toast.makeText(requireContext(), "Разрешение не предоставлено", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLauncher()
        setActions()

    }

    private fun registerLauncher() {
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                binding.carImage.setImageURI(uri)
                selectedUri = uri
            }
        }
    }

    private fun setActions() {
        binding.carImage.setOnClickListener {
            checkAndRequestPermission()
        }
        binding.btnSave.setOnClickListener {
            checkAndSaveCar()
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun checkAndSaveCar() {


        val byteArray = convertImageUriToByteArray(selectedUri!!)
        var savedImage: File? = null
        if (byteArray != null) {
            savedImage = saveImageToFile(byteArray, "${System.currentTimeMillis()}")
        } else {
            Log.d("TAGErrorSaving", "checkAndSaveCar: error on savingImage")
        }

        val uri = Uri.fromFile(savedImage)

        val inputStream: InputStream? = context?.contentResolver?.openInputStream(selectedUri!!)
        BitmapFactory.decodeStream(inputStream)

        val textName: String = binding.nameEdt.text.toString().ifBlank {
            binding.nameEdt.error = "Input Please"
            return
        }

        val textPrice: String = binding.priceEdt.text.toString().ifBlank {
            binding.priceEdt.error = "Input Please"
            return
        }

        val data = RoomArticles(
            currentPrice = textPrice,
            image = uri.toString(),
            name = textName,
            status = 0,
            tenantDate = null,
            tenantName = null
        )
        room?.addCar(data)
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        clearData()
        dismiss()
        onSaveListener.invoke()
    }

    private fun clearData() {
        binding.carImage.setImageDrawable(null)
        binding.nameEdt.text = null
        binding.priceEdt.text = null
    }

    private fun checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                checkPermission(Manifest.permission.READ_MEDIA_IMAGES) -> {
                    launchImagePicker()
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        } else {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                launchImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun launchImagePicker() {
        getContent.launch("image/*")
    }

    private fun saveImageToFile(byteArray: ByteArray, fileName: String): File {
        val file = File(requireContext().cacheDir, "$fileName.jpg")
        file.writeBytes(byteArray)
        return file
    }

    private fun convertImageUriToByteArray(uri: Uri): ByteArray? {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        inputStream?.use { stream ->
            val bitmap = BitmapFactory.decodeStream(stream)
            if (bitmap != null) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                return byteArrayOutputStream.toByteArray()
            }
        }
        return null
    }
}
