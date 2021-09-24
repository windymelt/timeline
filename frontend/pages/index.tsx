import * as React from "react"
import Layout from "../components/layout"
import Menu from "../components/menu"
import Event from "../components/event"

export default function HomePage() {
    return <Layout>
        <div className="ui grid">
            <Menu activeMenuName="home" />
            <div className="twelve wide stretched column">
                <div className="ui text container">
                    <h1>年表</h1>
                    <h2>
                        <a href="#">title</a><small>-- <i className="user icon"></i>@Windymelt</small>
                    </h2>
                    <Event />
                </div>
            </div>
        </div>
    </Layout>
}
