import SwiftUI
import Sodi
import RealmSwift

@main
struct iOSApp: SwiftUI.App, ISodi {
    
    init() {
        importModule(sodiModule: providersModule)
        importModule(sodiModule: ldsModule)
        importModule(sodiModule: repositoryModule)
        importModule(sodiModule: viewModelModule)
        
        let config = Realm.Configuration(
          // Set the new schema version. This must be greater than the previously used
          // version (if you've never set a schema version before, the version is 0).
          schemaVersion: 2,

          // Set the block which will be called automatically when opening a Realm with
          // a schema version lower than the one set above
          migrationBlock: { migration, oldSchemaVersion in
            // We haven’t migrated anything yet, so oldSchemaVersion == 0
            if (oldSchemaVersion < 1) {
              // Nothing to do!
              // Realm will automatically detect new properties and removed properties
              // And will update the schema on disk automatically
            }
          })

        // Tell Realm to use this new configuration object for the default Realm
        Realm.Configuration.defaultConfiguration = config
    }
    
    var body: some Scene {
        WindowGroup {
            MainView().edgesIgnoringSafeArea(.all)
        }
    }
}
