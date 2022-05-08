//
//  TextCounterView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct TextCounterView: View {
    
    @Binding var count: String
    let desc: String
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text(count)
                .font(.system(size: 16))
                .bold()
            Text(desc)
                .font(.system(size: 10))
        }.frame(alignment: .topLeading)
            .padding(EdgeInsets(top:4, leading: 2, bottom: 4, trailing: 2))
    }
}

struct TextCounterView_Previews: PreviewProvider {
    static var previews: some View {
        TextCounterView(count: .constant("957"), desc: "Публикации")
    }
}
