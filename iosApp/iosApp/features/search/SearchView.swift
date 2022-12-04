//
//  SearchView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi
import shared

struct SearchView: BaseView {
    
    @ObservedObject private var vm: SearchViewModel = instance()
    @State private var searchText: String = ""
    
    private let threeColumnGrid = [
        GridItem(.flexible(minimum: 40), spacing: 0),
        GridItem(.flexible(minimum: 40), spacing: 0),
        GridItem(.flexible(minimum: 40), spacing: 0),
    ]
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 0) {
                
                Divider()
                //------
                SearchBarView(text: $searchText)
                    .padding(EdgeInsets(top:8, leading: 0, bottom: 8, trailing: 0))
                
                //--- photo view
                LazyVGrid(columns: threeColumnGrid, alignment: .leading, spacing: 0) {
                    ForEach(searchResult, id: \.id) { postModel in
                        NavigationLink(
                            destination: SearchDetailsView(post: postModel)
                        ) {
                            ProfilePhotoView(
                                photoUrl: postModel.takeFirstPhoto().toUrl(),
                                hasMoreContent: postModel.hasMoreContent()
                            )
                        }
                    }
                }
            }
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .onAppear {
                vm.start()
            }.onDisappear {
                vm.stop()
            }
    }
    
    var searchResult: [PostEntity] {
        return vm.searchPosts(query: searchText)
    }
    
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView().previewLayout(.sizeThatFits)
    }
}
