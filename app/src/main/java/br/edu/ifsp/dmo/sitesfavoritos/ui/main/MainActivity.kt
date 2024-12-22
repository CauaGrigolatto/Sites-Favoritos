package br.edu.ifsp.dmo.sitesfavoritos.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.sitesfavoritos.R
import br.edu.ifsp.dmo.sitesfavoritos.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.sitesfavoritos.databinding.SitesDialogBinding
import br.edu.ifsp.dmo.sitesfavoritos.model.entity.Site
import br.edu.ifsp.dmo.sitesfavoritos.ui.adapter.SiteAdapter
import br.edu.ifsp.dmo.sitesfavoritos.ui.listener.SiteItemClickListener

class MainActivity : AppCompatActivity(), SiteItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SiteAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        configListeners()
        configRecyclerView()
        configObserver()
    }

    private fun configObserver() {
        viewModel.sites.observe(this, Observer { sites ->
            adapter.updateSites(sites)
        })
    }

    private fun configListeners() {
        binding.buttonAdd.setOnClickListener{handleAddSite()}
    }

    private fun handleAddSite() {
        val tela = layoutInflater.inflate(R.layout.sites_dialog, null)
        val bindingDialog = SitesDialogBinding.bind(tela)

        val builder = AlertDialog.Builder(this)
            .setView(tela)
            .setTitle(R.string.novo_site)
            .setPositiveButton(
                R.string.salvar,
                DialogInterface.OnClickListener{ dialog, which ->
                    viewModel.addSite(
                        Site(
                            bindingDialog.editTextApelido.text.toString(),
                            bindingDialog.editTextUrl.text.toString()
                        )
                    )
                    dialog.dismiss()
                }
            )
            .setNegativeButton(
                R.string.cancelar,
                DialogInterface.OnClickListener{ dialog, which ->
                    dialog.dismiss()
                }
            )

        val dialog = builder.create()
        dialog.show()
    }

    private fun configRecyclerView() {
        adapter = SiteAdapter(this, mutableListOf(), this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.recyclerViewSites.adapter = adapter
        binding.recyclerViewSites.layoutManager = layoutManager
    }

    override fun clickSiteItem(position: Int) {
        val site = viewModel.sites.value?.get(position)
        val mIntent = Intent(Intent.ACTION_VIEW)
        mIntent.setData((Uri.parse("http://" + site?.url)))
        startActivity(mIntent)
    }

    override fun clickFavoriteSiteItem(position: Int) {
        viewModel.toggleFavorite(position)
    }

    override fun clickDeleteSiteItem(position: Int) {
        val tela = layoutInflater.inflate(R.layout.deletar_dialog, null)

        val builder = AlertDialog.Builder(this@MainActivity)
            .setView(tela)
            .setTitle(R.string.deletar_site)
            .setPositiveButton(
                R.string.deletar,
                DialogInterface.OnClickListener{ dialog, which ->
                    viewModel.removeSite(position)
                    dialog.dismiss()
                }
            )
            .setNegativeButton(
                R.string.cancelar,
                DialogInterface.OnClickListener{ dialog, which ->
                    dialog.dismiss()
                }
            )

        val dialog = builder.create()
        dialog.show()
    }
}