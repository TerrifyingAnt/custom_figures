package xd.jg.custom_figures.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import xd.jg.custom_figures.ui.theme.CustomInversePrimary
import xd.jg.custom_figures.ui.theme.CustomPrimary

@Composable
fun CustomBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigationItems.CatalogScreen,
        BottomNavigationItems.BasketScreen,
        BottomNavigationItems.ChatScreen,
        BottomNavigationItems.AccountScreen
    )

    NavigationBar(
        modifier = modifier.fillMaxWidth().height(50.dp),
        containerColor = CustomPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = CustomInversePrimary,
                    selectedTextColor = Color.White,
                    selectedIconColor = Color.White,
                    unselectedIconColor = CustomInversePrimary,
                    indicatorColor = CustomPrimary
                ),
            )
        }
    }
}