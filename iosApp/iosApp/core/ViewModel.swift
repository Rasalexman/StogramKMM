//
//  File.swift
//  DiTest
//
//  Created by Alexander on 12.04.2022.
//

import Foundation
import shared

protocol ViewModel {
    var showAlert: Bool {get set}
    var errorText: String {get set}
    var isLoading: Bool {get set}
    var navigationTag: String? {get set}
    
    func onLoadingState()
    func onNavigateState(navTag: String)
    func onFailureState(errorMessage: String)
    func onEmptyState()
}

class BaseViewModel : ViewModel, ObservableObject {
    
    private var jobs = Array<Closeable>() // List of Kotlin Coroutine Jobs
    
    @Published var isLoading: Bool = false
    @Published var showAlert: Bool = false
    @Published var errorText: String = ""
    @Published var navigationTag: String? = nil
    
//    func reduceResultState<T: Any>(resultState: ResultState, onSuccess: (T) -> Void) {
//        switch resultState {
//        case .Loading:
//            onLoadingState()
//            break
//        case.Failure(message: let errorMessage):
//            onFailureState(errorMessage: errorMessage)
//            break
//        case .Navigate(navTag: let tag):
//            onNavigateState(navTag: tag)
//            break
//        case .Success(data: let resultData):
//            if let typedData: T = resultData as? T {
//                onSuccess(typedData)
//            }
//            break
//        case .Empty:
//            onEmptyState()
//            break
//        }
//    }
    
    func onLoadingState() {
        isLoading = !isLoading
    }
    
    func onNavigateState(navTag: String) {
        navigationTag = navTag
    }
    
    func onFailureState(errorMessage: String) {
        isLoading = false
        showAlert = true
        errorText = errorMessage
    }
    
    func addObserver(_ observer: Closeable) {
        jobs.append(observer)
    }
        
    func stop() {
        jobs.forEach { job in job.close() }
    }
    
    func onEmptyState() {
        
    }
}


