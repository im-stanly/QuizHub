import { Api } from "@/api/calls";
import { redirect } from "next/navigation";

export async function GET() {
    Api.Auth.logout()
    redirect("/")
}