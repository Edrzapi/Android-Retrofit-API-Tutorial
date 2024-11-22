import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.co.devfoundry.apitutorial.data.model.Pokemon
import uk.co.devfoundry.apitutorial.data.remote.Instance

class PokeViewModel : ViewModel() {
    private val _pokeList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokeList: StateFlow<List<Pokemon>> get() = _pokeList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    var currentOffset = 0 // Current offset for pagination
    private val limit = 20 // Number of items per page

    init {
        // Automatically load the first page
        fetchPokemonList(currentOffset)
    }

    private fun fetchPokemonList(offset: Int = 0) {
        // Prevents duplicate loads
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = "" // Reset error before fetching

            try {
                val response =
                    Instance.RetrofitInstance.api.getPokemonList(limit = limit, offset = offset)
                currentOffset = offset // Update the current offset

                // Concatenate new data for pagination
                _pokeList.value = response.results
            } catch (e: Exception) {
                _error.value = "Failed to load data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Next page function
    fun loadNextPage() {
        fetchPokemonList(currentOffset + limit)
    }

    // Previous page function
    fun loadPreviousPage() {
        if (currentOffset >= limit) { // Ensure offset doesn't go negative
            fetchPokemonList(currentOffset - limit)
        }
    }
}
