//
//  File.swift
//  DiTest
//
//  Created by Alexander on 12.04.2022.
//

import Foundation
//import shared
//
//protocol ISResult {
//
//}
//
//enum ResultState : ISResult {
//    case Loading
//    case Success(data: Any)
//    case Failure(message: String)
//    case Navigate(navTag: String)
//    case Empty
//}
//
//extension ISResult {
//}
//
//extension ResultState {
//
//    func applyIfSuccess<T: Any>(onSuccess: (T) -> Void) -> some ISResult {
//        switch self {
//        case .Success(let data):
//            if let typedData: T = data as? T {
//                onSuccess(typedData)
//            }
//            break
//        default: break
//        }
//        return self
//    }
//
//    func flatMapIfSuccess(onSuccess: (Any) -> ResultState) -> ResultState {
//        switch self {
//        case .Success(let data):
//            return onSuccess(data)
//        default: break
//        }
//        return self
//    }
//
//    func flatMapIfEmpty(onEmpty: () -> ResultState) -> ResultState {
//        switch self {
//        case .Empty:
//            return onEmpty()
//        default: break
//        }
//        return self
//    }
//
//    func flatMapIfFailure(onEmpty: (String) -> ResultState) -> ResultState {
//        switch self {
//        case .Failure(message: let errorMessage):
//            return onEmpty(errorMessage)
//        default: break
//        }
//        return self
//    }
//}
//
//let anySuccessState: ResultState = ResultState.Success(data: true)
//let emptyState = ResultState.Empty
//
//func failureState(message: String) -> ResultState {
//    return ResultState.Failure(message: message)
//}
//func successState<T: Any>(data: T) -> ResultState {
//    return ResultState.Success(data: data)
//}
