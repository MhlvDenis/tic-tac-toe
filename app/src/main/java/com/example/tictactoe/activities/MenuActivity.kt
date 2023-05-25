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
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityMenuBinding
import com.example.tictactoe.other.Constants
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

        binding.play.isEnabled = menuViewModel.isFormValid.value!!

        binding.play.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.scoreTable.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }
        binding.selectCrossesPlayer.setOnClickListener { provideSelectPlayer(Constants.CROSS) }
        binding.selectCrossesPlayerImg.setOnClickListener { provideSelectPlayer(Constants.CROSS) }
        binding.selectZeroesPlayer.setOnClickListener { provideSelectPlayer(Constants.ZERO) }
        binding.selectZeroesPlayerImg.setOnClickListener { provideSelectPlayer(Constants.ZERO) }
        initCrossesPlayer()
        initZeroesPlayer()

        menuViewModel.isFormValid.observe(this@MenuActivity, Observer {
            val isFormValid = it ?: return@Observer
            binding.play.isEnabled = isFormValid
        })
    }

    private fun provideSelectPlayer(type: String) {
        val title =
            if (type == Constants.CROSS)
                getString(R.string.new_player_crosses)
            else
                getString(R.string.new_player_zeroes)
        val addPlayer = getString(R.string.add_player)

        val input = EditText(this)
        input.hint = getString(R.string.hint_name)
        val selectImageButton = Button(this).apply {
            setOnClickListener {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, 3)
            }
            text = getString(R.string.select_image)
        }
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(input)
        linearLayout.addView(selectImageButton)

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(linearLayout)
            .setPositiveButton(addPlayer) { _, _ ->
                if (type == Constants.CROSS) {
                    menuViewModel.addCrossesPlayer(Player(name = input.text.toString(), imageUri = loadedImage))
                    initCrossesPlayer()
                    loadedImage = null
                } else {
                    menuViewModel.addZeroesPlayer(Player(name = input.text.toString(), imageUri = loadedImage))
                    initZeroesPlayer()
                    loadedImage = null
                }
            }
            .show()
    }

    private fun initCrossesPlayer() {
        val crossesPlayer = menuViewModel.getCrossesPlayer()
        if (crossesPlayer != null) {
            with (binding) {
                selectCrossesPlayer.visibility = View.GONE
                selectCrossesPlayerImg.visibility = View.VISIBLE
                glide.load(crossesPlayer.imageUri).centerCrop().into(selectCrossesPlayerImg)
            }
        }
    }

    private fun initZeroesPlayer() {
        val zeroesPlayer = menuViewModel.getZeroesPlayer()
        if (zeroesPlayer != null) {
            with (binding) {
                selectZeroesPlayer.visibility = View.GONE
                selectZeroesPlayerImg.visibility = View.VISIBLE
                glide.load(zeroesPlayer.imageUri).centerCrop().into(selectZeroesPlayerImg)
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