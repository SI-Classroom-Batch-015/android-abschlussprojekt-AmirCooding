import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemToCartBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemToFavoriteBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteAdapter
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteViewModel

class CartAdapter(
    private val dataset: List<Product>,
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemToCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageCartIV.load(product.image)
            priceCartTV.text = product.price.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ItemViewHolder {
        val binding = ItemToCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = dataset[position]
        holder.bind(product)

    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}