import SwiftUI
import Sodi
import URLImage
import URLImageStore

@main
struct iOSApp: SwiftUI.App, ISodi {
    
    let urlImageService = URLImageService(fileStore: URLImageFileStore(),
                                              inMemoryStore: URLImageInMemoryStore())
    
    init() {
        importModule(sodiModule: ldsModule)
        importModule(sodiModule: repositoryModule)
        importModule(sodiModule: viewModelModule)
    }
    
    var body: some Scene {
        WindowGroup {
            MainView().edgesIgnoringSafeArea(.all)
                .environment(\.urlImageService, urlImageService)
                .environment(\.urlImageOptions, URLImageOptions(loadOptions: [ .loadOnAppear, .cancelOnDisappear ]))
        }
    }
}
