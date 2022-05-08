//
//  StoryView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct StoryView: View {
    var story: StoryModel
    
    var body: some View {
        CircleImageView(photo: story.userPhoto, cWidth: 64, cHeight: 64, border: 2)
    }
}

struct StoryView_Previews: PreviewProvider {
    static var previews: some View {
        StoryView(story: StoryModel())
    }
}
