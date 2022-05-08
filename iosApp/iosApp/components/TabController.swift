//
//  TabController.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

class TabController: ObservableObject {
    @Published var activeTab = Tab.home

    func open(_ tab: Tab) {
        activeTab = tab
    }
}
