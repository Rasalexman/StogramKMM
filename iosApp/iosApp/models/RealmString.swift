//
//  RealmString.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import RealmSwift

class RealmString: Object {
    @Persisted var photo = ""
    
    convenience init(inputPhoto: String) {
        self.init()
        photo = inputPhoto
    }
}
