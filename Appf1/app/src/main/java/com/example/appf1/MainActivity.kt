package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.appf1.BaseActivity
// import androidx.recyclerview.widget.LinearLayoutManager // Ya no se usa aquí
import com.example.appf1.R
import com.example.appf1.activities.ScheduleActivity
import com.example.appf1.Utils
// import com.example.appf1.adapter.NewsAdapter // Ya no se usa aquí
import com.example.appf1.databinding.ActivityMainBinding
import model.Driver
import model.Race
import model.Team
import viewmodel.RaceViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val raceViewModel: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        // 1. Cargamos los datos en la BBDD
        preloadData()

        // 2. Observamos la PRÓXIMA carrera
        raceViewModel.nextRace.observe(this) { nextRace ->
            if (nextRace != null) {
                binding.txtRaceName.text = nextRace.name
                binding.txtRaceDate.text = nextRace.date
                binding.txtRaceCircuit.text = nextRace.circuit
                Utils.loadImageInto(nextRace.imageUrl, binding.ivCover, this)

                binding.cardNextRace.setOnClickListener {
                    val intent = Intent(this, GrandPrixDetailActivity::class.java)
                    intent.putExtra("RACE_ID", nextRace.id)
                    startActivity(intent)
                }
            } else {
                binding.txtRaceName.text = "Temporada Finalizada"
                binding.ivCover.visibility = View.GONE
            }
        }

        // --- ✅ SECCIÓN DE NOTICIAS ELIMINADA ---

        // 3. Lógica del Menú
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Ya estamos aquí
                R.id.nav_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    finish() // ✅ Cierra la actual
                    true
                }

                R.id.nav_results -> {
                    startActivity(Intent(this, ResultsActivity::class.java))
                    finish() // ✅ Cierra la actual
                    true
                }

                R.id.nav_fantasy -> {
                    startActivity(Intent(this, FantasyActivity::class.java))
                    finish() // ✅ Cierra la actual
                    true
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish() // ✅ Cierra la actual
                    true
                }

                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    // --- (Función preloadData COMPLETA con datos 2025) ---
    private fun preloadData() {
        // Usamos 'observeOnce' para comprobar si la BD está vacía
        raceViewModel.allRaces.observeOnce(this) { races ->
            if (races.isNullOrEmpty()) {

                // --- 1. DATOS DE CARRERAS ---
                val realRaces2025 = listOf(
                    // ... (Tu lista de 23 carreras se queda igual) ...
                    Race(name = "Gran Premio de Australia", imageUrl = "https://drive.google.com/uc?id=1CQ-rtzQJIMQIKfLfboKj9gBlpXPOtEka", circuit = "Circuito de Albert Park", date = "2025-03-16", location = "Melbourne, Australia", winner = "Carlos Sainz", laps = 58, time = "1:20:26.843", latitude = -37.8497, longitude = 144.9680, round = 1, country = "Australia"),
                    Race(name = "Gran Premio de China", imageUrl = "https://drive.google.com/uc?id=13527Fv6EFqtpv-01lRq3NPz4gmUwkNtw", circuit = "Circuito Internacional de Shanghái", date = "2025-03-23", location = "Shanghái, China", winner = "Max Verstappen", laps = 56, time = "1:40:52.554", latitude = 31.3389, longitude = 121.2200, round = 2, country = "China"),
                    Race(name = "Gran Premio de Japón", imageUrl = "https://drive.google.com/uc?id=1fvhAVuA11ZXUMK-iJEZhO4KtlB-IBU3D", circuit = "Suzuka", date = "2025-04-06", location = "Suzuka, Japón", winner = "Max Verstappen", laps = 53, time = "1:54:23.566", latitude = 34.8431, longitude = 136.5411, round = 3, country = "Japón"),
                    Race(name = "Gran Premio de Bahréin", imageUrl = "https://drive.google.com/uc?id=16PWQGAPt0MVqyPDQvTxDpZgpRx7XqVHS", circuit = "Circuito Internacional de Bahréin", date = "2025-04-13", location = "Sakhir, Bahréin", winner = null, laps = 57, time = null, latitude = 26.0325, longitude = 50.5104, round = 4, country = "Bahréin"),
                    Race(name = "Gran Premio de Arabia Saudí", imageUrl = "https://drive.google.com/uc?id=13z9Gl-pjfeg-YxbFAyp1i5NhogiDFxHm", circuit = "Jeddah Corniche Circuit", date = "2025-04-20", location = "Yeda, Arabia Saudí", winner = null, laps = 50, time = null, latitude = 21.6319, longitude = 39.1044, round = 5, country = "Arabia Saudí"),
                    Race(name = "Gran Premio de Miami", imageUrl = "https://drive.google.com/uc?id=1EZl9Tr_Pzdto6ndWHewMw9TrG0uQUibN", circuit = "Miami International Autodrome", date = "2025-05-04", location = "Miami, EEUU", winner = null, laps = 57, time = null, latitude = 25.9581, longitude = -80.2389, round = 6, country = "EEUU"),
                    Race(name = "Gran Premio de Emilia-Romaña", imageUrl = "https://drive.google.com/uc?id=11rFAg3zUGh44uqfwpWpelNj5A3GEN2Ou", circuit = "Autodromo Enzo e Dino Ferrari", date = "2025-05-18", location = "Imola, Italia", winner = null, laps = 63, time = null, latitude = 44.3439, longitude = 11.7167, round = 7, country = "Italia"),
                    Race(name = "Gran Premio de Mónaco", imageUrl = "https://drive.google.com/uc?id=1FcdpAssX6lVZG0a-RtKG_xS3za9KdN-G", circuit = "Circuito de Mónaco", date = "2025-05-25", location = "Montecarlo, Mónaco", winner = null, laps = 78, time = null, latitude = 43.7347, longitude = 7.4206, round = 8, country = "Mónaco"),
                    Race(name = "Gran Premio de España", imageUrl = "https://drive.google.com/uc?id=1ZmLLi1oP_nYl79fKwhwxarUMr47ECr9X", circuit = "Circuit de Barcelona-Catalunya", date = "2025-06-01", location = "Montmeló, España", winner = null, laps = 66, time = null, latitude = 41.5700, longitude = 2.2611, round = 9, country = "España"),
                    Race(name = "Gran Premio de Canadá", imageUrl = "https://drive.google.com/uc?id=1MoFlhaPQCTP689Yk8s9lnNLHpW7OvDzv", circuit = "Circuito Gilles Villeneuve", date = "2025-06-15", location = "Montreal, Canadá", winner = null, laps = 70, time = null, latitude = 45.5000, longitude = -73.5228, round = 10, country = "Canadá"),
                    Race(name = "Gran Premio de Austria", imageUrl = "https://drive.google.com/uc?id=1HUaqWgTbMX2Uczst1O41XxpyjKPZGVai", circuit = "Red Bull Ring", date = "2025-06-29", location = "Spielberg, Austria", winner = null, laps = 71, time = null, latitude = 47.2197, longitude = 14.7647, round = 11, country = "Austria"),
                    Race(name = "Gran Premio de Gran Bretaña", imageUrl = "https://drive.google.com/uc?id=1PiHBtSKSvNADT-oY1fjFNxR29hHF9KDD", circuit = "Silverstone Circuit", date = "2025-07-06", location = "Silverstone, UK", winner = null, laps = 52, time = null, latitude = 52.0786, longitude = -1.0169, round = 12, country = "Reino Unido"),
                    Race(name = "Gran Premio de Bélgica", imageUrl = "https://drive.google.com/uc?id=1edowfIOE0tHQWL0ZrXnVLcuONym2IStt", circuit = "Circuit de Spa-Francorchamps", date = "2025-07-27", location = "Stavelot, Bélgica", winner = null, laps = 44, time = null, latitude = 50.4372, longitude = 5.9714, round = 13, country = "Bélgica"),
                    Race(name = "Gran Premio de Hungría", imageUrl = "https://drive.google.com/uc?id=1xQb4jt0Ui_JFSo_wgSG2bLxqyxAX8pRe", circuit = "Hungaroring", date = "2025-08-03", location = "Mogyoród, Hungría", winner = null, laps = 70, time = null, latitude = 47.5789, longitude = 19.2486, round = 14, country = "Hungría"),
                    Race(name = "Gran Premio de los Países Bajos", imageUrl = "https://drive.google.com/uc?id=1gnENhnBbKc8HCD1ysXSY3wB0SKYk8ln6", circuit = "Circuit Zandvoort", date = "2025-08-31", location = "Zandvoort, Países Bajos", winner = null, laps = 72, time = null, latitude = 52.3888, longitude = 4.5409, round = 15, country = "Países Bajos"),
                    Race(name = "Gran Premio de Italia", imageUrl = "https://drive.google.com/uc?id=1ERt5ZTNT4dlBzs2yPQ8EwAewZVlsveWl", circuit = "Autodromo Nazionale Monza", date = "2025-09-07", location = "Monza, Italia", winner = null, laps = 53, time = null, latitude = 45.6156, longitude = 9.2811, round = 16, country = "Italia"),
                    Race(name = "Gran Premio de Azerbaiyán", imageUrl = "https://drive.google.com/uc?id=1_Wr40Al6gAkeIqTHxf45I9aT8_xgket4", circuit = "Baku City Circuit", date = "2025-09-21", location = "Bakú, Azerbaiyán", winner = null, laps = 51, time = null, latitude = 40.3725, longitude = 49.8533, round = 17, country = "Azerbaiyán"),
                    Race(name = "Gran Premio de Singapur", imageUrl = "https://drive.google.com/uc?id=1QUEjnT-lvpCaUtxfJKMh0QfQlkWFYck3", circuit = "Marina Bay Street Circuit", date = "2025-10-05", location = "Singapur", winner = null, laps = 62, time = null, latitude = 1.2914, longitude = 103.8640, round = 18, country = "Singapur"),
                    Race(name = "Gran Premio de Estados Unidos", imageUrl = "https://drive.google.com/uc?id=1VhK_1enw3LrYk_CQqB37l-17fkzzw1vR", circuit = "Circuit of the Americas", date = "2025-10-19", location = "Austin, EEUU", winner = null, laps = 56, time = null, latitude = 30.1328, longitude = -97.6411, round = 19, country = "EEUU"),
                    Race(name = "Gran Premio de la Ciudad de México", imageUrl = "https://drive.google.com/uc?id=1zTEb67_4i0gSbBgbwQ16ZclZNGK_k6iK", circuit = "Autódromo Hermanos Rodríguez", date = "2025-10-26", location = "Ciudad de México", winner = null, laps = 71, time = null, latitude = 19.4042, longitude = -99.0907, round = 20, country = "México"),
                    Race(name = "Gran Premio de São Paulo", imageUrl = "https://drive.google.com/uc?id=1LEV-m98IIhALGqNSynUXiNCYbnoqKRaG", circuit = "Autódromo José Carlos Pace", date = "2025-11-09", location = "São Paulo, Brasil", winner = null, laps = 71, time = null, latitude = -23.7036, longitude = -46.6997, round = 21, country = "Brasil"),
                    Race(name = "Gran Premio de Las Vegas", imageUrl = "https://drive.google.com/uc?id=1TGa3SEEZqMpghXoOcJsk-oaCUaRPvqXE", circuit = "Las Vegas Strip Circuit", date = "2025-11-22", location = "Las Vegas, EEUU", winner = null, laps = 50, time = null, latitude = 36.1147, longitude = -115.1728, round = 22, country = "EEUU"),
                    Race(name = "Gran Premio de Abu Dabi", imageUrl = "https://drive.google.com/uc?id=1k2v2spRuW4g-OiZFgdvdzF9bgKg7Svk6", circuit = "Yas Marina Circuit", date = "2025-12-07", location = "Abu Dabi, EAU", winner = null, laps = 58, time = null, latitude = 24.4672, longitude = 54.6031, round = 23, country = "EAU")
                )
                raceViewModel.insertAll(realRaces2025)

                // --- 2. DATOS DE PILOTOS (Con Puntos) ---
                val exampleDrivers = listOf(
                    // Red Bull
                    Driver("VER", "Max Verstappen", 1, "Red Bull", "Neerlandés", "https://drive.google.com/uc?id=1M1dyfrqlUOuxWij6IeeE0qJudtkkvq4T", 430),
                    Driver("PER", "Sergio Pérez", 11, "Red Bull", "Mexicano", "https://drive.google.com/uc?id=12L5uVsVb2DkKxWcTfPIj-m-vQgY6U8w1", 290),
                    // Ferrari
                    Driver("LEC", "Charles Leclerc", 16, "Ferrari", "Monegasco", "https://drive.google.com/uc?id=1r133L4INo4qbbrA6lcfyforSVzoh45Pb", 350),
                    Driver("HAM", "Lewis Hamilton", 44, "Ferrari", "Británico", "https://drive.google.com/uc?id=13KMvtcacPFpBcAkbWxmOS3ymJYe50X84", 220),
                    // McLaren
                    Driver("NOR", "Lando Norris", 4, "McLaren", "Británico", "https://drive.google.com/uc?id=1Dr5ZLG86_o0EGBTy-wyhZ8G6-ftnnGU-", 370),
                    Driver("PIA", "Oscar Piastri", 81, "McLaren", "Australiano", "https://drive.google.com/uc?id=18O7In5YXACh7HnLKWyOKwLzeAka0MuEI", 280),
                    // Mercedes
                    Driver("RUS", "George Russell", 63, "Mercedes", "Británico", "https://drive.google.com/uc?id=1R1vhWmG6JZUmtwTylo83w9n_0IpH6mla", 250),
                    Driver("ANT", "Kimi Antonelli", 12, "Mercedes", "Italiano", "https://drive.google.com/uc?id=1eJGG6KuQZVl87H5O-P0CJzyBD3s0ub_E", 0),
                    // Aston Martin
                    Driver("ALO", "Fernando Alonso", 14, "Aston Martin", "Español", "https://drive.google.com/uc?id=1zpy-OqtK0aReBERxCBdMiCAljhvTKLAN", 70),
                    Driver("STR", "Lance Stroll", 18, "Aston Martin", "Canadiense", "https://drive.google.com/uc?id=1TkwgIIDJcfXgMknKzhBMtArDZTs78ND6", 25),
                    // Williams
                    Driver("SAI", "Carlos Sainz", 55, "Williams", "Español", "https://drive.google.com/uc?id=1Wjdl4qQ9Bk7_BRQjAwZ0bRbNcLsaTJyt", 300),
                    Driver("ALB", "Alex Albon", 23, "Williams", "Tailandés", "https://drive.google.com/uc?id=17ZCTBn2qTVv_0S0icPmGXZrkVn7DmquE", 15),
                    // RB
                    Driver("TSU", "Yuki Tsunoda", 22, "RB", "Japonés", "https://drive.google.com/uc?id=1dCDP-IVwa9nUluxwZguEtUqZTfAaUxz0", 30),
                    Driver("HAD", "Isack Hadjar", 30, "RB", "Francés", "https://drive.google.com/uc?id=11B9K-shZSgfY2d9tDti4_KjvHmza0ZDa", 0),
                    // Alpine
                    Driver("GAS", "Pierre Gasly", 10, "Alpine", "Francés", "https://drive.google.com/uc?id=1X9XXevb1DVHO8z-tJyiCF31KXL9elu_G", 40),
                    Driver("DOO", "Jack Doohan", 6, "Alpine", "Australiano", "https://drive.google.com/uc?id=158tS-CEr-k-EUpAB9k-EKnDGwBTx18TE", 0),
                    // Haas
                    Driver("HUL", "Nico Hülkenberg", 27, "Sauber", "Alemán", "https://drive.google.com/uc?id=140P7Dkki538l4v58nBUpSyqhQDILRRv4", 50),
                    Driver("BEA", "Oliver Bearman", 87, "Haas", "Británico", "https://drive.google.com/uc?id=1o0aVmTBJgMOV___5HEkykYagJKduuHC6", 10),
                    Driver("MAG", "Kevin Magnussen", 20, "Haas", "Danés", "https://drive.google.com/uc?id=1Jj-z-g0-3Yp_Q9B-J8qY_J-j_k-8J_Bq", 5),
                    // Sauber
                    Driver("BOR", "Gabriel Bortoleto", 5, "Sauber", "Brasileño", "https://drive.google.com/uc?id=1uJ2Q2wGOrcULKZPk8bRrGcvprQ_iOvuc", 0)
                )
                raceViewModel.insertAllDrivers(exampleDrivers)


                // --- 3. DATOS DE EQUIPOS (Con Puntos) ---
                val exampleTeams = listOf(
                    Team("mclaren", "McLaren Formula 1 Team", "https://drive.google.com/uc?id=1yPL2VJoPBl0x_pKsVJSvVKV5_7BBIoBe", "https://drive.google.com/uc?id=1TRMFiJiYvgzbsjXxm1-T4WUd0w1R_JU8", 650),
                    Team("ferrari", "Scuderia Ferrari", "https://drive.google.com/uc?id=1ZnR84AoKMkmFoH9RRsJ6TGFTDYMoa0qn", "https://drive.google.com/uc?id=1kU1wvNn4C-pNOEpDZLZJQ3b8jOv2SxRK", 570),
                    Team("redbull", "Oracle Red Bull Racing", "https://drive.google.com/uc?id=1uNtXSKgjImXCpCdREHytog_1MuPuoIp6", "https://drive.google.com/uc?id=1Tbn2Xigmk9kHMXvDzkgXSIDJ-qQgzFeH", 720),
                    Team("mercedes", "Mercedes-AMG PETRONAS", "https://drive.google.com/uc?id=1Am5P39axU_rYnm38sgeBWjs271PAnRua", "https://drive.google.com/uc?id=1DkrbpST-2Z2WV_-zujRw7FZJLT8-_1Ok", 470),
                    Team("astonmartin", "Aston Martin Aramco", "https://drive.google.com/uc?id=1-L8niJ9T7J0HV4nI1s_wh6PRqcgl35i_", "https://drive.google.com/uc?id=1JOIvYgR1-Uq8lMyV8vHCe7cTpLd4b5oU", 95),
                    Team("williams", "Williams Racing", "https://drive.google.com/uc?id=1MRKSmMklsnSgrlDkkm_AGzHQsG4JOq7v", "https://drive.google.com/uc?id=1NmmfMeRvj0pgNZpRRcQH4eD4ybJGL8iP", 315),
                    Team("rb", "Visa Cash App RB", "https://drive.google.com/uc?id=1cC1RYq_nIKaBuy0LA0NG9dV4C-BDS0eN", "https://drive.google.com/uc?id=1_uoFPkoVn6p4fDuhOK5m4wJ0xCOo1jLy", 30),
                    Team("alpine", "Alpine F1 Team", "https://drive.google.com/uc?id=1DjPze77M6kXR7SvIhPVkI4CxDWImgtQ5", "https://drive.google.com/uc?id=17EoA5MoEeNskOQEg9k5gO74ie3kTXDaQ", 40),
                    Team("haas", "MoneyGram Haas F1 Team", "https://drive.google.com/uc?id=1nYA9Mf715VqNTIQkVR8GfTYfqxATFXxd", "https://drive.google.com/uc?id=1InQwI7szE4sgL6k2hdn7DESmkb134UIC", 15),
                    Team("sauber", "Stake F1 Team Kick Sauber", "https://drive.google.com/uc?id=1E82PhCyv3wc7vDC4kvDSkyuJT-l90QyY", "https://drive.google.com/uc?id=19i2294tSwEK_uIPqwgsF1-xVBB6ZvZhi", 50)
                )
                raceViewModel.insertAllTeams(exampleTeams)
            }
        }
    }
    // Función de extensión 'observeOnce'
    fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
        observe(owner, object : Observer<T> {
            override fun onChanged(value: T) {
                observer(value)
                removeObserver(this)
            }
        })
    }
}