//
//  SearchDetailsView.swift
//  Stogram
//
//  Created by Alexander on 03.05.2022.
//

import SwiftUI

struct SearchDetailsView: View {
    
    var post: PostModel
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false)  {
            VStack(alignment: .leading, spacing: 0) {
                Divider()
                PostView(post: self.post)
            }
        }
    }
}

struct SearchDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        SearchDetailsView(post: PostModel())
    }
}
