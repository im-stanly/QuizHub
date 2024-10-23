import SearchClientComponent from "./client"
import {Api} from "@/api/calls"
import {Quiz} from "@/api/model"

export default async function Page(props: { searchParams:{[key: string]: string | string[] | undefined} }){
    const query = props.searchParams["query"] as string
    const type = props.searchParams["type"] as string
    
    var data
    if(type=="tag"){
        data = await Api.Quiz.getQuizzesByTag(query)
    }
    if(type=="name"){
        data = await Api.Quiz.getQuizzesByName(query)
    }
    console.log(data)
    if(data == undefined) data = [] as Quiz[]

    return <SearchClientComponent quizzes={data} />
}