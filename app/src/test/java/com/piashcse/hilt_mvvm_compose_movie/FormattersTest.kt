package com.piashcse.hilt_mvvm_compose_movie

import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.asDuration
import com.piashcse.hilt_mvvm_compose_movie.utils.asLanguageName
import com.piashcse.hilt_mvvm_compose_movie.utils.asRating
import com.piashcse.hilt_mvvm_compose_movie.utils.asYear
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Locale

class FormattersTest {

    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.US)
    }

    @After
    fun tearDown() {
        Locale.setDefault(originalLocale)
    }

    @Test
    fun `year is extracted from a TMDB date`() {
        assertEquals("2026", "2026-11-18".asYear())
    }

    @Test
    fun `year is null for invalid input`() {
        assertNull(null.asYear())
        assertNull("".asYear())
        assertNull("unknown".asYear())
    }

    @Test
    fun `date is formatted for display`() {
        val formatted = "2026-11-18".asDisplayDate()
        assertTrue(formatted != null && formatted.contains("2026"))
        assertNull("".asDisplayDate())
    }

    @Test
    fun `language code is expanded to a name`() {
        assertEquals("English", "en".asLanguageName())
        assertNull(null.asLanguageName())
    }

    @Test
    fun `rating keeps one decimal`() {
        assertEquals("7.7", 7.666.asRating())
        assertEquals("8.0", 8.0.asRating())
    }

    @Test
    fun `duration is formatted in hours and minutes`() {
        assertEquals("2h 39m", 159.asDuration())
        assertEquals("45m", 45.asDuration())
        assertEquals("2h", 120.asDuration())
    }
}
