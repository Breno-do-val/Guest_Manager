package br.com.breno.convidados.service.repository

import android.content.ContentValues
import android.content.Context
import br.com.breno.convidados.constants.DataBaseConstants
import br.com.breno.convidados.service.model.GuestModel

class GuestRepository private constructor(context: Context) {

    // Created Singleton to avoid database concurrency

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun get(id: Int) : GuestModel? {

        var guest : GuestModel? = null
        return try {

            val db = mGuestDataBaseHelper.readableDatabase

            // The data to be shown by each query
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            // Using Android Cursor to access the result set returned by the query
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, presence)
            }

            cursor.close()
            guest
        } catch (e : Exception) {
            guest
        }
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        } catch (e : Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase

            // The data to be shown by each query
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            // Using Android Cursor to access the result set returned by the query
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }
            cursor.close()
            list
        } catch (e : Exception) {
            list
        }
    }

    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase

            // Using Android Cursor to access the result set returned by the query
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }
            cursor.close()
            list
        } catch (e : Exception) {
            list
        }
    }

    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {

            val db = mGuestDataBaseHelper.readableDatabase

            // Using Android Cursor to access the result set returned by the query
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }
            cursor.close()
            list
        } catch (e : Exception) {
            list
        }
    }


    fun update(guest: GuestModel) : Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            //Criteria for update
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            var args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e : Exception) {
            false
        }
    }

    fun delete(id: Int) : Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase

            //Criteria for delete
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            var args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME,  selection, args)
            true
        } catch (e : Exception) {
            false
        }
    }

}