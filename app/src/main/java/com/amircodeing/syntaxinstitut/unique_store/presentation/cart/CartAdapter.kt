import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemToCartBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.cart.CartViewModel

class CartAdapter(
    private val dataset: List<Product>,
    private val userId: String,
    private val viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemToCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageCartIV.load(product.image)
            priceCartTV.text = product.price.toString()
            countProduct.text = product.quantity.toString()

            increaseItem.setOnClickListener {
                viewModel.increaseItemQuantity(userId, product)
            }

            decreaseFromCart.setOnClickListener {
                viewModel.decreaseItemQuantity(userId, product)
            }

            deleteFromCart.setOnClickListener {
                viewModel.removeItemFromCart(userId, product)
            }
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
