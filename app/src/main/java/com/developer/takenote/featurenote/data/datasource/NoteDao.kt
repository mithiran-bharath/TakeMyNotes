package com.developer.takenote.featurenote.data.datasource

import androidx.room.*
import com.developer.takenote.featurenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from note WHERE id = :id")
    suspend fun getNotesById(id: Int): Note?

    @Query("SELECT * FROM note WHERE emailId = :mailId")
    fun getNotesFromMail(mailId: String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}