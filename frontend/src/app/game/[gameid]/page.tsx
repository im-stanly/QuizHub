import { NextResponse } from "next/server";
import GameClient from "./client";
import {Api} from "@/api/calls"

import { redirect } from "next/navigation"

export default async function GameComponent({params}: {params: {gameid: string}}) {
    // const game = await Api.Game.getGame(params.room)
    const user = await Api.Auth.getSignedInUser()
    const game = await Api.Game.getGame(params.gameid)
    console.log(user, params.gameid)
    if(user == undefined || params.gameid == undefined || game == undefined) {
        return redirect("/")
    }
    return <GameClient room={params.gameid} username={user?.username} game={game}/>
}