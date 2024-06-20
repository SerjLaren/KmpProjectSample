//
//  TestFile.swift
//  iosApp
//
//  Created by developer on 16.06.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import sharedumbrella

struct SomeTestData {
    let a: Int64
    let b: String
    let c: Float
}

class TestFile {
    
    init() {
        testFunc()
    }
    
    func testFunc() {
        let memoryCache = MemoryCache()
        memoryCache.put(key: "qwe", data: SomeTestData(a: 123, b: "345", c: 446.99))
        let getFromCache = memoryCache.get(key: "qwe") as! SomeTestData
        let asdasd = getFromCache.a + 100
//        let repo = UserPostRepository()
//        repo.getUserPostsRemote { result, error in
//            if result is RemoteResultSuccess {
//                let aaa = (result as! RemoteResultSuccess).data as! [UserPost]
//                let bbb = aaa
//                repo.saveUserPostsLocal(userPosts: aaa, completionHandler: {_ in })
//                let ccc = repo.getUserPostsLocal { result, error in
//                    let loccclll = result
//                    let cnt = loccclll?.count
//                }
//            }
//        }
    }
}
