package com.example.telegram.ui.objects

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tg.R
import com.example.telegram.ui.screens.contacts.ContactsFragment
import com.example.telegram.ui.screens.settings.SettingsFragment
import com.example.telegram.utilits.APP_ACTIVITY
import com.example.telegram.database.USER
import com.example.telegram.ui.screens.groups.AddContactsFragment
import com.example.telegram.utilits.downloadAndSetImage
import com.example.telegram.utilits.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader

/* Обьект реализующий боковое меню Navigation Drawer */

class AppDrawer {

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mCurrentProfile:ProfileDrawerItem

    fun create() {
        /* Создания бокового меню */
        initLoader()
        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
    }

    fun disableDrawer() {
        /* Отключение выдвигающего меню */
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer() {
        /* Включение выдвигающего меню */
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()
        }
    }

    @SuppressLint("ResourceType")
    private fun createDrawer() {
        /* Создание дравера */
        mDrawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withToolbar( APP_ACTIVITY.mToolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Создать группу")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_create_groups),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Контакты")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_contacts),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Настройки")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_menu_settings)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                   clickToItem(position)
                    return false
                }
            }).build()

    }
///////////////////////////////////////////////////////////
    private fun clickToItem(position:Int){
        when(position) {
            1 -> replaceFragment(AddContactsFragment())
            3 -> replaceFragment(SettingsFragment())
            2 -> replaceFragment(ContactsFragment())
        }
    }
//////////////////////////////////////////////////////////
    private fun createHeader() {
        /* Создание хедера*/
        mCurrentProfile = ProfileDrawerItem()
            .withName(USER.fullname)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)
            .withIdentifier(200)
        mHeader = AccountHeaderBuilder()
            .withActivity(APP_ACTIVITY)
            .withHeaderBackground(R.color.md_deep_purple_400)
            .addProfiles(
                mCurrentProfile
            ).build()
    }

    fun updateHeader(){
        /* Обновления хедера */
        mCurrentProfile
            .withName(USER.fullname)
            .withEmail(USER.phone)
            .withIcon(USER.photoUrl)

        mHeader.updateProfile(mCurrentProfile)

    }
///////////////////////////////////////////////////////////////
    private fun initLoader(){
        /* Инициализация лоадера для загрузки картинок в хедер */
        DrawerImageLoader.init(object :AbstractDrawerImageLoader(){
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }
    ////////////////////////////////////////////////////////////////////
}