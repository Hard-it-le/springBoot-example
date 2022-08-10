#  第一次写 dockerfile
FROM alpine

LABEL  maintained="余嘉乐" \

deees=dasdas \
dasdsa=sdasdasdas

# 制定构建参数，仅限 RUN 环境使用
ARG aaaa=aaaa

# 指定环境变量，为RUN以及 CMD 使用
ENV param=123123213

RUN echo ${param}

RUN ["echo","${aaaa}"]
CMD sleep 10;echo success