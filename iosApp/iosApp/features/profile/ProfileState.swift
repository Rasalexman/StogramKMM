//
//  ProfileState.swift
//  iosApp
//
//  Created by Alexander on 04.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct ProfileState {
    let user: IUser?
    var posts: [PostEntity] = []
}
