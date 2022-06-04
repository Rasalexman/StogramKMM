import SwiftUI
import Sodi
import URLImage
import URLImageStore

@main
struct iOSApp: SwiftUI.App, ISodi {
    
    let urlImageService = URLImageService(fileStore: URLImageFileStore(),
                                              inMemoryStore: URLImageInMemoryStore())
    
    init() {
        importModule(sodiModule: databaseModule)
        importModule(sodiModule: ldsModule)
        importModule(sodiModule: repositoryModule)
        importModule(sodiModule: viewModelModule)
    }
    
    var body: some Scene {
        WindowGroup {
            MainView()
                .environment(\.urlImageService, urlImageService)
                .environment(\.urlImageOptions, URLImageOptions(loadOptions: [ .loadOnAppear, .cancelOnDisappear ]))
        }
    }
}
