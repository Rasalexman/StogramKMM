//
//  MainViewModel.swift
//  Stogram
//
//  Created by Alexander on 18.04.2022.
//

import Foundation
import Sodi
import shared


final class MainViewModel : BaseViewModel {
    
    private let userRepository: IUserRepository = instance()
    
    @Published var hasUser: Bool = false
    
    func start() {
        
    }
    
}
