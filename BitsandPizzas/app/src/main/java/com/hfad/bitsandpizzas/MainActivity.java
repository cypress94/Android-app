package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ShareActionProvider;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    // Глава 9
    private ShareActionProvider shareActionProvider;
    // Глава 10
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    public class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // реакция на щелчок элемента
            // Переключения фрагмента во фрейме
            // Изменение заголовка на Панели Действий в соответстви с выбранным макетом
            // и Закрытие выдвижной панели
            selectItem(i);

        }
    }

    private void selectItem(int position){
        Fragment fragment;
        currentPosition = position;

        switch(position){
            case 1:
                //fragment = new PizzasFragment();
                fragment = new PizzaMaterialFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoriesFragment();
                break;
            default:
                fragment = new TopFragment();

        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        // Назначение заголовка на панели действий
        setActionBarTitle(position);
        // Закрыть выдвижную панель
        drawerLayout.closeDrawer(drawerList);


    }

    // Назначение заголовка на панели действий
    private void setActionBarTitle(int i){
        String title;
        if (i == 0)
            title = getResources().getString(R.string.app_name);
        else
            title = titles[i];
        getActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Глава 10.
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView)findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,// выбранный вариант подсвечивется
                titles
        ));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }else {
            selectItem(0);
        }

        // Создание ActionBarDrawerToggle для управления выдвижной панелью
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer){
            // Вызывается при закрытии выдвижной панели
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // вызывает onPrepareOptionsMenu
            }
            // Выхывается при открытии выдвижной панели
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu(); // вызывает onPrepareOptionsMenu
            }


        };
        drawerLayout.setDrawerListener(drawerToggle);

        // включить значок "Выдвижной Панели (ВП)" на панель действий
        // включить кнопку "Вверх", чтобы ее м. б. использовать для управления ВП
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Чтобы заголовок ПД соответсвовал фрагменту при нажатии кнопки НАЗАД
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("visible_fragment");

                if (fragment instanceof TopFragment)
                    currentPosition = 0;
                //if (fragment instanceof PizzasFragment)
                if (fragment instanceof PizzaMaterialFragment)
                    currentPosition = 1;
                if (fragment instanceof PastaFragment)
                    currentPosition = 2;
                if (fragment instanceof StoriesFragment)
                    currentPosition = 3;
                // Установка правильного заголовка на ПД
                setActionBarTitle(currentPosition);
                // Подсветка в списке правильного элемента на выдвижной панели
                drawerList.setItemChecked(currentPosition, true);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // Если Выдвижная Панель открыта, скрыть элемент "Share",
        // поскольку он накладывается на панель
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);// открыта ли панель?
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    // Создание меню (Панели Действий со всеми элементами из файла menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // заполнение меню. Если есть элементы то добавляются на ПД
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider)menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Чтобы объект ActionBarDrawerToggle реагировал на щелчки
        // onOptionsItemSelected возвр true, если был щелчок на об. drawerToggle
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch(item.getItemId()){
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }
}
