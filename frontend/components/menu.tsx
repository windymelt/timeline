import * as React from "react"
import 'fomantic-ui-css/semantic.min.css' // XXX: 名前が元のままになっているけどこれで良い

type MenuProps = {
    activeMenuName: String
}

export default function Menu(props: MenuProps) {
    const homeActive = props.activeMenuName == "home" ? " active" : ""
    const editActive = props.activeMenuName == "edit" ? " active" : ""
    return <div className="four wide column">
        <div className="ui vertical fluid tabular menu">
            <a className={"item" + homeActive} href="/">
                玄関
            </a>
            <a className="item">
                年表を探す <i className="hard hat icon"></i>
            </a>
            <a className={"item" + editActive} href="/-/edit">
                年表編纂室 <i className="hard hat icon"></i>
            </a>
        </div>
    </div>
}
