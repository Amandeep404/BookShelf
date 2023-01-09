package com.example.bookshelf.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.api.RetrofitInstance
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import com.example.bookshelf.repo.BooksRepository
import com.example.bookshelf.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class BooksViewModel(
    val booksRepository: BooksRepository
) : ViewModel(){
    val newBooks : MutableLiveData<Resource<BooksApiResponse>> = MutableLiveData()

    val searchBooks : MutableLiveData<Resource<BooksApiResponse>> = MutableLiveData()
    val searchPageNumber = 1

    val booksAllData :MutableLiveData<Book> = MutableLiveData()

    init {
        getNewBooks()
    }

    fun getNewBooks() = viewModelScope.launch {
        newBooks.postValue(Resource.Loading())

        val response = booksRepository.getNewBooks()
        newBooks.postValue(handleResponse(response))
    }

    fun searchBooks(searchQuery : String) = viewModelScope.launch {
        searchBooks.postValue(Resource.Loading())

        val response = booksRepository.searchNews(searchQuery, searchPageNumber)
        searchBooks.postValue(handleResponse(response))
    }

    fun getFullData(isbn : String) {
        viewModelScope.launch {
            booksAllData.value = RetrofitInstance.booksApi.isbnCall(isbn)

        }
    }

    private fun handleResponse(response : Response<BooksApiResponse>) :Resource<BooksApiResponse>{
        if (response.isSuccessful){
            response.body().let {resultResponse ->
                return Resource.Success(resultResponse!!)

            }
        }
        return Resource.Error(response.message())
    }

    private fun handleBookResponse(response : Response<Book>) :Resource<Book>{
        if (response.isSuccessful){
            response.body().let {resultResponse ->
                return Resource.Success(resultResponse!!)

            }
        }
        return Resource.Error(response.message())
    }

    fun saveBooks(book:Book) = viewModelScope.launch {
        booksRepository.upsert(book)
    }
    fun getSavedBooks() = booksRepository.getSavedBook()

    fun deleteBook(book:Book) = viewModelScope.launch {
        booksRepository.deleteBook(book)
    }
}