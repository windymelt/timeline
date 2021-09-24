import * as React from "react"
import 'fomantic-ui-css/semantic.min.css' // XXX: 名前が元のままになっているけどこれで良い

export default function Layout({ children }) {
    return <>
        <div className="ui fixed inverted menu">
            <div className="ui container">
                <a href="/" className="header item">サクサク年表君</a>
                <a href="#" className="item"><i className="book icon"></i>読む</a>
                <a href="#" className="item"><i className="edit icon"></i>書く</a>
            </div>
        </div>
        <h1 className="ui center aligned header">サクサク年表君</h1>
        <main>{children}</main>
        <address>サクサク年表君 by <a href="https://twitter.com/windymelt">@Windymelt</a></address>
    </>
}
