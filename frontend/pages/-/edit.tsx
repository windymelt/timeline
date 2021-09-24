import * as React from "react"
import Layout from "../../components/layout"
import Menu from "../../components/menu"

export default function Edit() {
    return <Layout>
        <div className="ui grid inverted">
            <Menu activeMenuName="edit" />
            <div className="ui text container">
                <h2>年表を編集する</h2>
                <form action="" className="ui form">
                    <div>
                        <h3>年表選択</h3>
                        <div className="field">
                            <button className="ui button">年表を新規作成</button>
                            <label>年表を選ぶ</label>
                            <select name="" id="" className="ui fluid dropdown">
                                <option value="12345">ほげ年表</option>
                            </select>
                        </div>
                        <div className="field">
                            <label>Title</label>
                            <input type="text" name="timeline-title" placeholder="Some Title" />
                        </div>
                    </div>
                    <div>
                        <h3>イベントを選択</h3>
                        <div className="field">
                            <button className="ui button">イベントを新規作成</button>
                            <label>イベントを選ぶ</label>
                            <select name="" id="" className="ui fluid dropdown">
                                <option value="12345">ほげイベント</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <h3>イベントを編集</h3>
                        <div className="field">
                            <label htmlFor="">イベント名</label>
                            <input type="text" name="" id="" placeholder="ほげイベント" />
                        </div>
                        <div className="field">
                            <label htmlFor="">日時</label>
                            <input type="datetime-local" name="" id="" />
                            <select name="" id="" className="ui fluid dropdown">
                                <option value="12345">時刻単位</option>
                                <option value="12345">日単位（時刻以降は無視します）</option>
                                <option value="12345">月単位（日以降は無視します）</option>
                                <option value="12345">年単位（月以降は無視します）</option>
                            </select>
                        </div>
                        <div className="field">
                            <label htmlFor="">説明文</label>
                            <textarea name="" id="" cols={30} rows={10} placeholder="出来事の説明を書きます"></textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </Layout>
}
