import SwiftUI
import Sodi

@main
struct iOSApp: SwiftUI.App, ISodi {
    
    init() {
        importModule(sodiModule: ldsModule)
        importModule(sodiModule: repositoryModule)
        importModule(sodiModule: viewModelModule)
    }
    
    var body: some Scene {
        WindowGroup {
            MainView().edgesIgnoringSafeArea(.all)
        }
    }
}
