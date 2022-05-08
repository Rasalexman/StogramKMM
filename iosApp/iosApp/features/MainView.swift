//
//  MainView.swift
//  Stogram
//
//  Created by Alexander on 18.04.2022.
//

import SwiftUI
import Sodi

struct MainView: BaseView {
    
    @ObservedObject private var mainViewModel: MainViewModel = instance()
    @StateObject private var tabController = TabController()
    
    init() {
        UITabBar.appearance().isTranslucent = false
        UITabBar.appearance().unselectedItemTintColor = UIColor(Color.primary)
        //UITabBar.appearance().barTintColor = UIColor(Color("tab_background"))
    }
    
    var body: some View {
        NavigationView {
            TabView(selection: $tabController.activeTab) {
                HomeView()
                    .tag(Tab.home)
                    .tabItem { Label("Лента", systemImage: "house") }
                    .navigationBarTitleDisplayMode(.inline)
                                    
                SearchView()
                    .tag(Tab.search)
                    .tabItem { Label("Поиск", systemImage: "magnifyingglass") }
                    .navigationBarTitleDisplayMode(.inline)
                                    
                CreateView()
                    .tag(Tab.create)
                    .tabItem { Label("Добавить", systemImage: "plus.app") }
                
                ReactionsView()
                    .tag(Tab.reactions)
                    .tabItem { Label("Реакции", systemImage: "bolt.heart.fill") }
                    .navigationBarTitleDisplayMode(.inline)
                
                ProfileView()
                    .tag(Tab.settings)
                    .tabItem { Label("Профиль", systemImage: "person.fill") }
                    .navigationBarTitleDisplayMode(.inline)
            }
            .edgesIgnoringSafeArea(.top)
            .environmentObject(tabController)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text("Stogram")
                        .font(.largeTitle.bold())
                        .accessibilityAddTraits(.isHeader)
                }
            }
        }
    }
    
    static func getNavLink(onSelection: Binding<String?>) -> some View {
        return NavigationLink(destination: MainView().navigationBarBackButtonHidden(true), tag: NavTag.MAIN.rawValue, selection: onSelection) { EmptyView() }
        
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
