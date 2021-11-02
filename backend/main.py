#!/usr/bin/env python3
# coding: utf-8

from typing import Optional
import fastapi
from fastapi import Header

app = fastapi.FastAPI()

fakeDb = {
    '2daymc': {
        'name': 'Kovács Imre',
        'password': 'bonobo'
    },
    'gojoGS': {
        'name': 'Bartha Zoltán',
        'password': 'agyhalott'
    },
    'lilizsupos1': {
        'name': 'Zsupos Lilla',
        'password': 'panda'
    },
    'minik': {
        'name': 'Fakó Dominik',
        'password': 'adminadmin'
    }
}


@app.get('/auth')
async def auth(username: Optional[str] = Header(None),
               password: Optional[str] = Header(None)):

    if username is None or password is None:
        return {'status': 'Invalid request'}

    if username not in fakeDb:
        return {'status': 'User not found'}

    if password != fakeDb[username]['password']:
        return {'status': 'Failed'}

    return {'status': 'Successful'}


def main():
    pass


if __name__ == '__main__':
    main()
