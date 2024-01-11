package com.example.starwarscharactersactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarscharactersactivity.presenter.home.CharactersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openCharacterFragment()
    }

    private fun openCharacterFragment() {
        val fragment = CharactersFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, fragment)
            .commit()
    }
}