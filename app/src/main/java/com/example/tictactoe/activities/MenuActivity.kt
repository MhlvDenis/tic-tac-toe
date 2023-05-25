package com.example.tictactoe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import com.example.tictactoe.databinding.ActivityMenuBinding
import com.example.tictactoe.players.Player
import com.example.tictactoe.viewmodels.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding: ActivityMenuBinding

    @Inject
    lateinit var glide: RequestManager

    private var loadedImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.play.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.scoreTable.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }
        binding.selectCrossesPlayer.setOnClickListener { provideSelectCrossesPlayer() }
        binding.selectCrossesPlayerImg.setOnClickListener { provideSelectCrossesPlayer() }
        binding.selectZeroesPlayer.setOnClickListener { provideSelectZeroesPlayer() }
        binding.selectZeroesPlayerImg.setOnClickListener { provideSelectZeroesPlayer() }
        initCrossesPlayer()
        initZeroesPlayer()
    }

    private fun provideSelectCrossesPlayer() {
        val input = EditText(this)
        val selectImageButton = Button(this).apply {
            setOnClickListener {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, 3)
            }
        }
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(input)
        linearLayout.addView(selectImageButton)
        AlertDialog.Builder(this)
            .setTitle("New crosses player")
            .setView(linearLayout)
            .setPositiveButton("Add player") { dialog, which ->
                menuViewModel.addCrossesPlayer(Player(name = input.text.toString()))
                initCrossesPlayer()
            }
            .show()
    }

    private fun provideSelectZeroesPlayer() {
        val input = EditText(this)
        val selectImageButton = Button(this).apply {
            setOnClickListener {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, 3)
            }
        }
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(input)
        linearLayout.addView(selectImageButton)
        AlertDialog.Builder(this)
            .setTitle("New zeroes player")
            .setView(linearLayout)
            .setPositiveButton("Add player") { dialog, which ->
                menuViewModel.addZeroesPlayer(Player(name = input.text.toString()))
                initZeroesPlayer()
            }
            .show()
    }

    private fun initCrossesPlayer() {
        val crossesPlayer = menuViewModel.getCrossesPlayer()
        if (crossesPlayer != null) {
            with (binding) {
                selectCrossesPlayer.visibility = View.GONE
                selectCrossesPlayerImg.visibility = View.VISIBLE
                glide.load(loadedImage).into(selectCrossesPlayerImg)
                loadedImage = null
            }
        }
    }

    private fun initZeroesPlayer() {
        val zeroesPlayer = menuViewModel.getZeroesPlayer()
        if (zeroesPlayer != null) {
            with (binding) {
                selectZeroesPlayer.visibility = View.GONE
                selectZeroesPlayerImg.visibility = View.VISIBLE
                glide.load(loadedImage).into(selectZeroesPlayerImg)
                loadedImage = null
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3 && resultCode == RESULT_OK) {
            loadedImage = data?.data
        }
    }
}