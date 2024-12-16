package com.golden.gate.ui.details.dialog

import android.Manifest
import android.app.Notification.Action
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
import com.golden.gate.databinding.DialogEditCarBinding
import com.golden.gate.ui.details.ActionCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.ByteArrayOutputStream
import java.io.File

class EditCarDialog : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogEditCarBinding::bind)
    private var imageByte: ByteArray? = null
    private var uri: Uri? = null
    private val room = AppDataBase.getInstance()
    private var data: RoomArticles? = null
    private var actionListener: ActionCallback? = null

    companion object {
        private const val ARG_DATA = "data"

        fun newInstance(data: RoomArticles): EditCarDialog {
            val fragment = EditCarDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_DATA, data)
            }
            return fragment
        }
    }

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
        return inflater.inflate(R.layout.dialog_edit_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionListener = parentFragment as ActionCallback
        setData()
        setActions()
        registerLauncher()

    }

    private fun registerLauncher() {
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            var imageFile: File? = null
            uri.let {
                binding.carImage.setImageURI(uri)
                imageByte = convertImageUriToByteArray(uri!!)
                if (imageByte != null) {
                    imageFile = saveImageToFile(imageByte!!, "${System.currentTimeMillis()}")
                }
                this.uri = Uri.fromFile(imageFile)
            }
        }
    }

    private fun setActions() {

        binding.delete.setOnClickListener {
            room?.deleteCar(data!!.id)
            actionListener!!.onDelete()
            dismiss()
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.carImage.setOnClickListener {
            checkAndRequestPermission()
        }

        binding.btnSave.setOnClickListener {
            if (isDataFull()) {
                val data = RoomArticles(
                    name = binding.nameEdt.text.toString(),
                    tenantDate = binding.dateEdt.text.toString(),
                    tenantName = binding.tenantNameEdt.text.toString(),
                    image = uri.toString(),
                    currentPrice = binding.priceEdt.text.toString(),
                    status = 1,
                    id = this.data!!.id
                )
                Log.d("TagSavedData", "setActions: $data")
                room?.updateData(data)
                setData()
                dismiss()
                actionListener?.onSaveListener()
            }
        }
    }


    private fun isDataFull(): Boolean {

        if (uri == null) {
            Toast.makeText(context, "uri", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.nameEdt.text.isBlank()) {
            Toast.makeText(context, "car name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.dateEdt.text!!.isBlank() || binding.dateEdt.length() > 23) {
            Toast.makeText(context, "date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.priceEdt.text.isBlank()) {
            Toast.makeText(context, "price", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.tenantNameEdt.text.isBlank()) {
            Toast.makeText(context, "tenant name", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun setData() {
        data = arguments?.getParcelable(ARG_DATA)
        uri = Uri.parse(data!!.image)
        binding.nameEdt.setText(data?.name)
        binding.priceEdt.setText(data?.currentPrice)
        binding.dateEdt.setText(data?.tenantDate)
        binding.tenantNameEdt.setText(data?.tenantName)
        binding.carImage.setImageURI(Uri.parse(data?.image))
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