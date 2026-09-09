package com.example.mycinema

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycinema.databinding.ActivityMainBinding
import com.example.mycinema.databinding.ItemSimilarMovieBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId = savedInstanceState?.getString(KEY_MOVIE_ID)
            ?: intent.getStringExtra(EXTRA_MOVIE_ID)
            ?: MovieRepository.DEFAULT_MOVIE_ID
        showMovie(MovieRepository.byId(movieId))

        binding.btnRent.setOnClickListener {
            toast(getString(R.string.detail_rent_message, getString(movie.title)))
        }
        binding.btnBuy.setOnClickListener {
            toast(getString(R.string.detail_buy_message, getString(movie.title)))
        }
        binding.btnSave.setOnClickListener {
            toast(getString(R.string.detail_saved, getString(movie.title)))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_MOVIE_ID, movie.id)
    }

    private fun showMovie(selected: Movie) {
        movie = selected
        val title = getString(selected.title)

        binding.imgBackdrop.setBlurredImage(selected.poster)
        binding.imgBackdrop.contentDescription = getString(R.string.detail_backdrop_description, title)
        binding.imgPoster.setImageResource(selected.poster)
        binding.imgPoster.contentDescription = getString(R.string.detail_poster_description, title)
        binding.tvTitle.text = title
        binding.tvGenre.setText(selected.genre)
        binding.tvSynopsis.setText(selected.synopsis)
        binding.tvRating.text = getString(R.string.detail_rating, selected.rating.toString())
        binding.tvMatch.text = getString(R.string.detail_match, selected.matchPercent)

        renderSimilar(MovieRepository.similarTo(selected))
        binding.scroll.smoothScrollTo(0, 0)
    }

    private fun renderSimilar(similar: List<Movie>) {
        val container = binding.similarContainer
        container.removeAllViews()
        val inflater = LayoutInflater.from(this)
        similar.forEach { other ->
            val item = ItemSimilarMovieBinding.inflate(inflater, container, false)
            val otherTitle = getString(other.title)
            item.imgSimilarPoster.setImageResource(other.poster)
            item.imgSimilarPoster.contentDescription = getString(R.string.detail_poster_description, otherTitle)
            item.tvSimilarTitle.text = otherTitle
            item.root.setOnClickListener { showMovie(other) }
            container.addView(item.root)
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        private const val KEY_MOVIE_ID = "movie_id"
    }
}
