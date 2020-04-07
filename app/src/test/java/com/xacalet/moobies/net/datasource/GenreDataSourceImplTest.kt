package com.xacalet.moobies.net.datasource

import org.junit.Test
import javax.inject.Inject

class GenreDataSourceImplTest {

    @Inject
    lateinit var genreApiDataSourceImpl: GenreDataSourceImpl

    @Test
    fun test() {
        genreApiDataSourceImpl.hashCode()
    }
}
