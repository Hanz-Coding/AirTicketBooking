package hanz.coding.airticketbooking.domain.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hanz.coding.airticketbooking.domain.FlightModel
import hanz.coding.airticketbooking.domain.LocationModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MainRepository {
    private var flightModel: FlightModel? = null
    fun getCurrentFlight() = flightModel
    fun setCurrentFlight(flightModel: FlightModel) {
        this.flightModel = flightModel
    }

    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://airticketbooking1-bdab4-default-rtdb.asia-southeast1.firebasedatabase.app")

    fun loadLocation(): Flow<List<LocationModel>> = callbackFlow {
        val refLocation = firebaseDatabase.getReference("Locations")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val locations = snapshot.children.mapNotNull {
                    it.getValue(LocationModel::class.java)
                }
                trySend(locations).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        refLocation.addValueEventListener(listener)
        awaitClose { refLocation.removeEventListener(listener) }
    }

    fun loadFlights(from: String, to: String): Flow<List<FlightModel>> = callbackFlow {
        val refFlights = firebaseDatabase.getReference("Flights")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val flights = snapshot.children.mapNotNull {
                    it.getValue(FlightModel::class.java)
                }
                trySend(flights).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        refFlights.addValueEventListener(listener)
        awaitClose { refFlights.removeEventListener(listener) }
    }
}
