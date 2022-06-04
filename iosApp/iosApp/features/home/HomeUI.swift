//
//  HomeUI.swift
//  iosApp
//
//  Created by Alexander on 04.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct HomeUI {
    let posts: [PostEntity]
    let stories: [UserEntity]
    
    public static let EMPTY = HomeUI(posts: [], stories: [])
}
