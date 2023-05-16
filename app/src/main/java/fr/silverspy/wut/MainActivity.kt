package fr.silverspy.wut

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import fr.silverspy.wut.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var websiteListAdapter: WebsiteListAdapter
    private lateinit var websiteRepository: WebsiteRepository
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        websiteRepository = WebsiteRepository(this)
        updateWebsiteList()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = websiteListAdapter

        // Lancer le service de surveillance des sites Web
        val intent = Intent(this, WebsiteMonitorService::class.java)
        startService(intent)
    }

    override fun onResume() {
        super.onResume()
        updateWebsiteList()
    }

    private fun updateWebsiteList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val websites = websiteRepository.getAllWebsites()
            withContext(Dispatchers.Main) {
                val adapter = WebsiteListAdapter(websites)
                binding.recyclerView.adapter = adapter
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_website -> {
                // Gérer l'action "Ajouter un site Web"
                true
            }
            R.id.action_edit_websites -> {
                // Gérer l'action "Modifier les sites Web"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
