package p42.projetabc;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import p42.projetabc.databinding.ActivityMainBinding;
import p42.projetabc.book.BookAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BookAdapter adapterBook;
    private String toSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_book, R.id.navigation_author).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    public boolean onSupportNavigateUp(){
        NavController nav = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        return nav.navigateUp() || super.onSupportNavigateUp();
    }
}
